#pragma once
#include "FileShoppingBasket.h"
#include <string>

class HTMLShoppingBasket : public FileShoppingBasket
{
public:
	void writeToFile() override;
	void displayShoppingBasket() const override;
};