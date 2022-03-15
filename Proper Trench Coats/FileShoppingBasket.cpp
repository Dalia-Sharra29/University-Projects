#include "FileShoppingBasket.h"

FileShoppingBasket::FileShoppingBasket() : ShoppingBasket{}, fileName{ "" }
{

}

void FileShoppingBasket::setFileName(const std::string& fileName)
{
	this->fileName = fileName;
}