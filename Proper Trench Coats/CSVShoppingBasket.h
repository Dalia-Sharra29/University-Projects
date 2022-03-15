#pragma once
#include "FileShoppingBasket.h"
#include <string>

class CSVShoppingBasket : public FileShoppingBasket
{
public:
	void writeToFile() override;
	void displayShoppingBasket() const override;
};