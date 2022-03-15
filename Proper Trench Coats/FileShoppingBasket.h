#pragma once
#include "ShoppingBasket.h"

class FileShoppingBasket : public ShoppingBasket
{
protected:
	std::string fileName;

public:
	FileShoppingBasket();
	virtual ~FileShoppingBasket() {}

	void setFileName(const std::string& fileName);
	virtual void writeToFile() = 0;
	virtual void displayShoppingBasket() const = 0;
};