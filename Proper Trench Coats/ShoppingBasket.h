#pragma once
#include <vector>
#include "TrenchCoat.h"

class ShoppingBasket
{
protected:
	std::vector<TrenchCoat> coats;
	double currentSum;

public:
	ShoppingBasket();

	void add(const TrenchCoat& trench_coat);

	double getCurrentSum();

	bool isEmpty();

	int getCoatsNumber();

	std::vector<TrenchCoat> getCoats();

	virtual ~ShoppingBasket() {}

};
