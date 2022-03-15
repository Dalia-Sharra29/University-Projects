#include "CoatsTableModel.h"

int CoatsTableModel::rowCount(const QModelIndex& parent) const
{
    return this->shoppingBasket.getCoatsNumber();
}

int CoatsTableModel::columnCount(const QModelIndex& parent) const
{
    return 5;
}

QVariant CoatsTableModel::data(const QModelIndex& index, int role) const
{
    int row = index.row();
    int col = index.column();
    TrenchCoat currentCoats = this->shoppingBasket.getCoats()[row];

    if (role == Qt::DisplayRole)
    {
        switch (col)
        {
        case 0:
            return QString::number(currentCoats.getSize());
        case 1:
            return QString::fromStdString(currentCoats.getColour());
        case 2:
            return QString::number(currentCoats.getPrice());
        case 3:
            return QString::number(currentCoats.getQuantity());
        case 4:
            return QString::fromStdString(currentCoats.getPhotograph());
        default:
            break;
        }
    }

    return QVariant();
}

QVariant CoatsTableModel::headerData(int section, Qt::Orientation orientation, int role) const
{
    if (role == Qt::DisplayRole)
    {
        if (orientation == Qt::Horizontal)
        {
            switch (section)
            {
            case 0:
                return QString("Size");
            case 1:
                return QString("Colour");
            case 2:
                return QString("Price");
            case 3:
                return QString("Quantity");
            case 4:
                return QString("Photograph");
            default:
                break;
            }
        }
    }

    return QVariant();
}
