#pragma once
#include <qabstractitemmodel.h>
#include "ShoppingBasket.h"

class CoatsTableModel : public QAbstractTableModel
{
	Q_OBJECT
private:
	ShoppingBasket& shoppingBasket;
	
public:
	CoatsTableModel(ShoppingBasket& sb) : shoppingBasket{ sb } { }

	int rowCount(const QModelIndex& parent = QModelIndex{}) const override;

	int columnCount(const QModelIndex& parent = QModelIndex{}) const override;

	QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override;

	QVariant headerData(int section, Qt::Orientation orientation, int role = Qt::DisplayRole) const override;

};

