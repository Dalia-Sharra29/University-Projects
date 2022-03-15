#include "TrenchCoatsGUI.h"
#include <QtWidgets/QApplication>
#include "Repository.h"
#include "FileShoppingBasket.h"
#include "CSVShoppingBasket.h"
#include "HTMLShoppingBasket.h"
#include "Service.h"
#include <iostream>
#include <memory>
#include "GUI.h"
#include <QInputDialog>
#include <QDir>
#include <QString>
#include <QMessageBox>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
	Repository repository{ "trench_coats.txt" };
	std::unique_ptr<FileShoppingBasket> bag;
	
	bool ok;
	QString text = QInputDialog::getText(0, "Select file type",
		"File type <csv or html>:", QLineEdit::Normal,
		QDir::home().dirName(), &ok);
	if (ok && !text.isEmpty())
	{
		std::string fileType = text.toStdString();
		if (text == "csv")
		{
			bag = std::make_unique<CSVShoppingBasket>();

		}
		else if (text == "html")
		{
			bag = std::make_unique<HTMLShoppingBasket>();

		}
		else
		{
			QMessageBox::critical(0, "Error", "Bad input - the file type must be < csv > or < html >!");
			return 0;
		}
	}

	Service service{ repository, bag.get(), TrenchCoatValidator{} };
	GUI gui{service};
	gui.show();
	
    return a.exec();
}
