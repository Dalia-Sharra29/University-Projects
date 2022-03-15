#include "ShoppingBasket.h"

ShoppingBasket::ShoppingBasket()
{
	this->currentSum = 0;
}

void ShoppingBasket::add(const TrenchCoat& trench_coat)
{
	this->currentSum = this->currentSum + trench_coat.getPrice();
	this->coats.push_back(trench_coat);
}


double ShoppingBasket::getCurrentSum()
{
	return this->currentSum;
}


bool ShoppingBasket::isEmpty()
{
	return this->coats.size() == 0;
}

int ShoppingBasket::getCoatsNumber()
{
	return this->coats.size();
}

std::vector<TrenchCoat> ShoppingBasket::getCoats()
{
	return this->coats;
}
