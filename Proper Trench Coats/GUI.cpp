#include "GUI.h"
#include <qlayout.h>
#include <qformlayout.h>
#include <qgridlayout.h>
#include <Qmessagebox.h>
#include <QLabel>
#include <QtWidgets/QApplication>
#include <QInputDialog>
#include <QDir>
#include <QHeaderView>
#include <QKeySequence>

GUI::GUI(Service& s) : service { s }
{
	this->initGUI();
	this->connectSignalsAndSlotsMain();
}

void GUI::initGUI()
{
	QHBoxLayout* mainLayout = new QHBoxLayout{ this };

	this->userButton = new QPushButton{ "User" };
	this->adminButton = new QPushButton{ "Admin" };
	QGridLayout* mainButtonsLayout = new QGridLayout{};
	mainButtonsLayout->addWidget(this->userButton, 0, 1);
	mainButtonsLayout->addWidget(this->adminButton, 0, 0);
	
	mainLayout->addLayout(mainButtonsLayout);
}

void GUI::populateList()
{
	this->coatsListWidget->clear();

	std::vector<TrenchCoat> allCoats = this->service.getAllCoats();
	for (TrenchCoat& trench_coat : allCoats)
	{
		this->coatsListWidget->addItem(QString::fromStdString(std::to_string(trench_coat.getSize()) + " - " + trench_coat.getColour()));
	}
}

void GUI::connectSignalsAndSlotsAdmin()
{
	QObject::connect(this->coatsListWidget, &QListWidget::itemSelectionChanged, [this]() {
		int selectedIndex = this->getSelectedIndex();
		if (selectedIndex < 0)
			return;
		TrenchCoat trench_coat = this->service.getAllCoats()[selectedIndex];
		this->priceLineEdit->setText(QString::fromStdString(std::to_string(trench_coat.getPrice())));
		this->sizeLineEdit->setText(QString::fromStdString(std::to_string(trench_coat.getSize())));
		this->quantityLineEdit->setText(QString::fromStdString(std::to_string(trench_coat.getQuantity())));
		this->colourLineEdit->setText(QString::fromStdString(trench_coat.getColour()));
		this->photographLineEdit->setText(QString::fromStdString(trench_coat.getPhotograph()));

		});

	QObject::connect(this->addButton, &QPushButton::clicked, this, &GUI::addCoat);
	QObject::connect(this->deleteButton, &QPushButton::clicked, this, &GUI::deleteCoat);
	QObject::connect(this->updateButton, &QPushButton::clicked, this, &GUI::updateCoat);
	QObject::connect(this->undoButton, &QPushButton::clicked, this, &GUI::undo);
	QObject::connect(this->redoButton, &QPushButton::clicked, this, &GUI::redo);
	QObject::connect(this->undoShortcut, &QShortcut::activated, this, &GUI::undo);
	QObject::connect(this->redoShortcut, &QShortcut::activated, this, &GUI::redo);

}

void GUI::connectSignalsAndSlotsUser()
{
	QObject::connect(this->listUserButton, &QPushButton::clicked, this, &GUI::userList);
	QObject::connect(this->addUserButton, &QPushButton::clicked, this, &GUI::addUser);
	QObject::connect(this->nextUserButton, &QPushButton::clicked, this, &GUI::nextUser);
	QObject::connect(this->saveUserButton, &QPushButton::clicked, this, &GUI::saveUser);
	QObject::connect(this->showUserButton, &QPushButton::clicked, this, &GUI::showUser);
}

void GUI::connectSignalsAndSlotsMain()
{
	QObject::connect(this->adminButton, &QPushButton::clicked, this, &GUI::createAdmin);
	QObject::connect(this->userButton, &QPushButton::clicked, this, &GUI::createUser);
}

int GUI::getSelectedIndex() const
{
	QModelIndexList selectedIndexes = this->coatsListWidget->selectionModel()->selectedIndexes();
	if (selectedIndexes.size() == 0)
	{
		this->sizeLineEdit->clear();
		this->colourLineEdit->clear();
		this->priceLineEdit->clear();
		this->quantityLineEdit->clear();
		this->photographLineEdit->clear();
		return -1;
	}

	int selectedIndex = selectedIndexes.at(0).row();
	return selectedIndex;
}

void GUI::addCoat()
{

	if (this->sizeLineEdit->text().toStdString() == "" || this->colourLineEdit->text().toStdString() == "" || this->priceLineEdit->text().toStdString() == "" || this->quantityLineEdit->text().toStdString() == "" || this->photographLineEdit->text().toStdString() == "")
	{
		QMessageBox::critical(this, "Error", "You have to introduce all the arguments for a trench coat!");
		return;
	}
	int size, quantity;
	double price;
	std::string colour, photograph;

	try
	{
		size = stoi(this->sizeLineEdit->text().toStdString());
	}
	catch (std::exception& exception)
	{
		QMessageBox::critical(this, "Error", exception.what());
		return;
	}

	try
	{
		colour = this->colourLineEdit->text().toStdString();
	}
	catch (std::exception& exception)
	{
		QMessageBox::critical(this, "Error", exception.what());
		return;
	}

	try
	{
		price = stod(this->priceLineEdit->text().toStdString());
	}
	catch (std::exception& exception)
	{
		QMessageBox::critical(this, "Error", exception.what());
		return;
	}

	try
	{
		quantity = stoi(this->quantityLineEdit->text().toStdString());
	}
	catch (std::exception& exception)
	{
		QMessageBox::critical(this, "Error", exception.what());
		return;
	}

	try
	{
		photograph = this->photographLineEdit->text().toStdString();
	}
	catch (std::exception& exception)
	{
		QMessageBox::critical(this, "Error", exception.what());
		return;
	}

	try
	{
		this->service.addTrenchCoatToRepository(size, colour, price, quantity, photograph);
	}
	catch (std::exception& exception)
	{
		QMessageBox::critical(this, "Error", exception.what());
	}
	this->populateList();

	int lastElement = this->service.getAllCoats().size() - 1;
	this->coatsListWidget->setCurrentRow(lastElement);
}

void GUI::deleteCoat()
{
	int selectedIndex = this->getSelectedIndex();
	if (selectedIndex < 0)
	{
		QMessageBox::critical(this, "Error", "No trench coat was selected!");
		return;
	}

	TrenchCoat trench_coat = this->service.getAllCoats()[selectedIndex];
	this->service.deleteTrenchCoatFromRepository(trench_coat.getSize(), trench_coat.getColour());

	this->populateList();
}

void GUI::updateCoat()
{
	int selectedIndex = this->getSelectedIndex();

	if (selectedIndex < 0)
	{
		QMessageBox::critical(this, "Error", "No trench coat was selected!");
		return;
	}

	int new_size, new_quantity;
	std::string new_colour, new_photograph;
	double new_price;

	try
	{
		new_size = stoi(this->sizeLineEdit->text().toStdString());
	}
	catch (std::exception& exception)
	{
		QMessageBox::critical(this, "Error", exception.what());
		return;
	}

	try
	{
		new_colour = this->colourLineEdit->text().toStdString();
	}
	catch (std::exception& exception)
	{
		QMessageBox::critical(this, "Error", exception.what());
		return;
	}

	try
	{
		new_price = stod(this->priceLineEdit->text().toStdString());
	}
	catch (std::exception& exception)
	{
		QMessageBox::critical(this, "Error", exception.what());
		return;
	}

	try
	{
		new_quantity = stoi(this->quantityLineEdit->text().toStdString());
	}
	catch (std::exception& exception)
	{
		QMessageBox::critical(this, "Error", exception.what());
		return;
	}

	try
	{
		new_photograph = this->photographLineEdit->text().toStdString();
	}
	catch (std::exception& exception)
	{
		QMessageBox::critical(this, "Error", exception.what());
		return;
	}

	TrenchCoat trench_coat = this->service.getAllCoats()[selectedIndex];
	try {
		this->service.updateTrenchCoat(trench_coat.getSize(), trench_coat.getColour(), new_size, new_colour, new_price, new_quantity, new_photograph);
	}
	catch (std::exception& exception)
	{
		QMessageBox::critical(this, "Error", exception.what());
	}

	this->populateList();
	this->coatsListWidget->setCurrentRow(selectedIndex);

}

void GUI::undo()
{
	int selectedIndex = this->getSelectedIndex();
	try {
		this->service.undo();
	}
	catch (...)
	{
		QMessageBox::information(0, "Information", QString("No more undos!"));
	}
	
	this->populateList();
	this->coatsListWidget->setCurrentRow(selectedIndex);
}

void GUI::redo()
{
	int selectedIndex = this->getSelectedIndex();
	try {
		this->service.redo();
	}
	catch (...)
	{
		QMessageBox::information(0, "Information", QString("No more redos!"));
	}
	this->populateList();
	this->coatsListWidget->setCurrentRow(selectedIndex);
}

void GUI::createAdmin()
{
	QVBoxLayout* adminLayout = new QVBoxLayout{ this };
	QWidget* admin = new QWidget{ };

	this->coatsListWidget = new QListWidget{};
	this->sizeLineEdit = new QLineEdit{};
	this->colourLineEdit = new QLineEdit{};
	this->priceLineEdit = new QLineEdit{};
	this->quantityLineEdit = new QLineEdit{};
	this->photographLineEdit = new QLineEdit{};
	this->addButton = new QPushButton{ "Add" };
	this->deleteButton = new QPushButton{ "Delete" };
	this->updateButton = new QPushButton{ "Update" };
	this->undoButton = new QPushButton{ "Undo" };
	this->redoButton = new QPushButton{ "Redo" };
	this->undoShortcut = new QShortcut(QKeySequence(Qt::CTRL + Qt::Key_Z), this->undoButton);
	this->redoShortcut = new QShortcut(QKeySequence(Qt::CTRL + Qt::Key_Y), this->redoButton);


	adminLayout->addWidget(this->coatsListWidget);

	QFormLayout* coatsDetailsLayout = new QFormLayout{};
	coatsDetailsLayout->addRow("Size", this->sizeLineEdit);
	coatsDetailsLayout->addRow("Colour", this->colourLineEdit);
	coatsDetailsLayout->addRow("Price", this->priceLineEdit);
	coatsDetailsLayout->addRow("Quantity", this->quantityLineEdit);
	coatsDetailsLayout->addRow("Photograph", this->photographLineEdit);

	adminLayout->addLayout(coatsDetailsLayout);

	QGridLayout* buttonsLayout = new QGridLayout{};
	buttonsLayout->addWidget(this->addButton, 0, 0);
	buttonsLayout->addWidget(this->deleteButton, 0, 1);
	buttonsLayout->addWidget(this->updateButton, 0, 2);

	adminLayout->addLayout(buttonsLayout);

	QHBoxLayout* buttonsLayout2 = new QHBoxLayout{ this };

	buttonsLayout2->addWidget(this->undoButton);
	buttonsLayout2->addWidget(this->redoButton);

	adminLayout->addLayout(buttonsLayout2);

	admin->setWindowTitle(QApplication::translate("admin", "Admin"));
	admin->setLayout(adminLayout);
	this->populateList();
	this->connectSignalsAndSlotsAdmin();
	admin->show();
	this->hide();
}

void GUI::createUser()
{
	QWidget* user = new QWidget{ };
	QVBoxLayout* userLayout = new QVBoxLayout{ this };

	this->shoppingBasketListWidget = new QListWidget{};
	this->addUserButton = new QPushButton{ "Add" };
	this->nextUserButton = new QPushButton{ "Next" };
	this->saveUserButton = new QPushButton{ "Save" };
	this->showUserButton = new QPushButton{ "Show" };
	this->listUserButton = new QPushButton{ "List" };
	this->sizeUserLineEdit = new QLineEdit{};
	this->colourUserLineEdit = new QLineEdit{};
	this->priceUserLineEdit = new QLineEdit{};
	this->quantityUserLineEdit = new QLineEdit{};
	this->photographUserLineEdit = new QLineEdit{};


	userLayout->addWidget(shoppingBasketListWidget);

	this->sizeUserLineEdit->setReadOnly(true);
	this->colourUserLineEdit->setReadOnly(true);
	this->priceUserLineEdit->setReadOnly(true);
	this->quantityUserLineEdit->setReadOnly(true);
	this->photographUserLineEdit->setReadOnly(true);

	QFormLayout* coatDetailsLayout = new QFormLayout{};
	coatDetailsLayout->addRow("Size", this->sizeUserLineEdit);
	coatDetailsLayout->addRow("Colour", this->colourUserLineEdit);
	coatDetailsLayout->addRow("Price", this->priceUserLineEdit);
	coatDetailsLayout->addRow("Quantity", this->quantityUserLineEdit);
	coatDetailsLayout->addRow("Photograph", this->photographUserLineEdit);

	userLayout->addLayout(coatDetailsLayout);

	QGridLayout* userButtonsLayout = new QGridLayout{};

	QHBoxLayout* userButtons2 = new QHBoxLayout{ this };

	userButtons2->addWidget(this->addUserButton);
	userButtons2->addWidget(this->nextUserButton);
	userLayout->addLayout(userButtons2);

	userButtonsLayout->addWidget(this->saveUserButton, 1, 0);
	userButtonsLayout->addWidget(this->showUserButton, 1, 1);
	userButtonsLayout->addWidget(this->listUserButton, 1, 2);

	userLayout->addLayout(userButtonsLayout);

	user->setWindowTitle(QApplication::translate("user", "User"));
	user->setLayout(userLayout);
	this->connectSignalsAndSlotsUser();
	user->show();
	this->hide();
}

void GUI::displayCurrent()
{
	TrenchCoat trench_coat = this->service.getRepo().getCurrentCoat();
	this->priceUserLineEdit->setText(QString::number(trench_coat.getPrice()));
	this->sizeUserLineEdit->setText(QString::number(trench_coat.getSize()));
	this->quantityUserLineEdit->setText(QString::number(trench_coat.getQuantity()));
	this->colourUserLineEdit->setText(QString::fromStdString(trench_coat.getColour()));
	this->photographUserLineEdit->setText(QString::fromStdString(trench_coat.getPhotograph()));
	this->service.getRepo().getCurrentCoat().play();
}

void GUI::addUser()
{
	TrenchCoat trench_coat = this->service.getRepo().getCurrentCoat();
	this->service.addTrenchCoatToShoppingBasket(trench_coat);
	this->shoppingBasketListWidget->addItem(QString::fromStdString(std::to_string(trench_coat.getSize()) + " - " + trench_coat.getColour()));
	QMessageBox::information(0, "The Current Sum",
		QString("The current sum is %1").arg(QString::number(this->service.getCurrentSum())));
	this->service.nextTrenchCoatShoppingBasket(trench_coat.getSize());
	this->displayCurrent();
}

void GUI::nextUser()
{
	TrenchCoat trench_coat = this->service.getRepo().getCurrentCoat();
	this->service.nextTrenchCoatShoppingBasket(trench_coat.getSize());
	this->displayCurrent();
}

void GUI::saveUser()
{
	bool ok;
	QString file = QInputDialog::getText(0, "Choose file name",
		"Give file name:", QLineEdit::Normal,
		QDir::home().dirName(), &ok);
	std::string fileName;
	if (ok && !file.isEmpty())
	{
		fileName = file.toStdString();
	}
	else
	{
		QMessageBox::critical(0, "Error", "You have to provide a file name!");
		return;
	}
	this->service.saveShoppingBasket(fileName);

}

void GUI::showUser()
{
	this->service.openShoppingBasket();

	this->coatsTableView = new QTableView();
	this->tableModel = new CoatsTableModel{ *this->service.getShoppingBasket() };
	this->coatsTableView->horizontalHeader()->setSectionResizeMode(QHeaderView::Stretch);
	this->coatsTableView->setModel(this->tableModel);
	this->coatsTableView->resizeColumnsToContents();
	this->coatsTableView->setWindowTitle(QApplication::translate("coatstableview", "CoatsTableView"));
	this->coatsTableView->show();
}

void GUI::userList()
{
	bool ok;
	QString size = QInputDialog::getText(0, "Select size",
		"Give size:", QLineEdit::Normal,
		QDir::home().dirName(), &ok);
	int coatSize = 0;
	if (ok && !size.isEmpty())
	{
		coatSize = size.toInt();
	}
	this->service.firstCoat(coatSize);
	this->displayCurrent();

}
