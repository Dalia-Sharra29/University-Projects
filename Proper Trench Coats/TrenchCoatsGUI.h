#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_TrenchCoatsGUI.h"

class TrenchCoatsGUI : public QMainWindow
{
    Q_OBJECT

public:
    TrenchCoatsGUI(QWidget *parent = Q_NULLPTR);

private:
    Ui::TrenchCoatsGUIClass ui;
};
