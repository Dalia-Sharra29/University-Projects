#pragma once
#include <qwidget.h>
#include "Service.h"
#include <qlistwidget.h>
#include <qlineedit.h>
#include <qpushbutton.h>
#include "CoatsTableModel.h"
#include <qtableview>
#include <QShortcut>

class GUI : public QWidget
{
private:
	Service& service;
	CoatsTableModel* tableModel;

	//Graphical elements
	QListWidget* coatsListWidget, *shoppingBasketListWidget;
	QLineEdit* sizeLineEdit, * colourLineEdit, * priceLineEdit, * quantityLineEdit, * photographLineEdit, * sizeUserLineEdit, * colourUserLineEdit, * priceUserLineEdit, * quantityUserLineEdit, * photographUserLineEdit;
	QPushButton* addButton, * deleteButton, * updateButton, *undoButton, *redoButton, * addUserButton, * nextUserButton, * saveUserButton, * showUserButton, *listUserButton, *userButton, *adminButton;
	QTableView* coatsTableView;
	QShortcut* undoShortcut;
	QShortcut* redoShortcut;


public:
	GUI(Service& s);

private:
	void initGUI();
	void populateList();
	void connectSignalsAndSlotsAdmin();
	void connectSignalsAndSlotsUser();
	void connectSignalsAndSlotsMain();

	int getSelectedIndex() const;
	void addCoat();
	void deleteCoat();
	void updateCoat();
	void undo();
	void redo();
	void createAdmin();
	void createUser();
	void displayCurrent();
	void addUser();
	void nextUser();
	void saveUser();
	void showUser();
	void userList();
};


