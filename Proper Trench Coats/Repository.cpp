#include "Repository.h"
#include <iterator>
#include <fstream>

Repository::Repository(const std::string& fileName)
{
	//std::vector<TrenchCoat>{};
	coatsFileName = fileName;
	loadCoatsFromFile();
}

Repository::~Repository()
{
	writeCoatsToFile();
}

void Repository::loadCoatsFromFile()
{
	if (coatsFileName == "")
		return;
	TrenchCoat trench_coat;
	std::ifstream fin(coatsFileName);
	if (!fin.is_open())
		return;
	while (fin >> trench_coat)
	{
		if (std::find(coats_list.begin(), coats_list.end(), trench_coat) == coats_list.end())
		{
			coats_list.push_back(trench_coat);
		}
	}
	fin.close();
}

void Repository::writeCoatsToFile()
{
	if (coatsFileName == "")
		return;
	std::ofstream fout(coatsFileName);
	if (!fout.is_open())
		return;
	for (const TrenchCoat& trench_coat : coats_list)
	{
		fout << trench_coat;
	}
	fout.close();
}

void Repository::addTrenchCoat(const TrenchCoat& trench_coat)
{
	auto iterator = std::find(coats_list.begin(), coats_list.end(), trench_coat);
	if (iterator != coats_list.end() && coats_list.size() != 0)
	{
		throw RepositoryException(std::string("Trench coat already in the list!"));
	}
	coats_list.push_back(trench_coat);
	writeCoatsToFile();
}


TrenchCoat Repository::findTrenchCoat(const TrenchCoat& trench_coat)
{
	auto iterator = std::find(coats_list.begin(), coats_list.end(), trench_coat);
	if (iterator != coats_list.end())
	{
		int position = iterator - coats_list.begin();
		return coats_list.at(position);
	}
}

void Repository::deleteTrenchCoat(const TrenchCoat& trench_coat)
{
	auto iterator = std::find(coats_list.begin(), coats_list.end(), trench_coat);
	if (iterator == coats_list.end())
	{
		throw RepositoryException(std::string("Trench coat does not exist!"));
	}
	coats_list.erase(iterator);
	writeCoatsToFile();
}

void Repository::updateTrenchCoat(const TrenchCoat& trench_coat, const TrenchCoat& new_trench_coat)
{
	auto iterator = std::find(coats_list.begin(), coats_list.end(), trench_coat);
	if (iterator == coats_list.end())
	{
		throw RepositoryException(std::string("Trench coat does not exist!"));
	}
	*iterator = new_trench_coat;
	writeCoatsToFile();
}

int Repository::getNumberOfCoats()
{
	return coats_list.size();
}

std::vector<TrenchCoat> Repository::getAllCoats()
{
	return coats_list;
}

/*
std::vector<TrenchCoat> Repository::getShoppingBasket()
{
	return userShoppingBasket;
}
*/

TrenchCoat Repository::getCurrentCoat()
{
	return this->coats_list[this->current];
}

void Repository::setCurrent(int &position)
{
	this->current = position;
}

void Repository::play()
{
	if (this->coats_list.size() == 0)
		return;
	this->current = 0;
	TrenchCoat currentCoat = this->getCurrentCoat();
	currentCoat.play();
}

void Repository::next()
{
	if (this->coats_list.size() - 1 == this->current)
		this->current = 0;
	else {
		this->current++;
	}
}

void Repository::first()
{
	this->current = 0;
}

std::vector<TrenchCoat> Repository::getTrenchCoatsBySize(int size)
{
	std::vector<TrenchCoat> filteredTrenchCoats;
	if (size == 0)
		return coats_list;
	std::copy_if(coats_list.begin(), coats_list.end(), std::back_inserter(filteredTrenchCoats), [size](const TrenchCoat& trench_coat) {return trench_coat.getSize() == size; });
	if (filteredTrenchCoats.size() == 0)
		throw RepositoryException(std::string("No trench coat with this size!"));
	return filteredTrenchCoats;
}

/*
int Repository::addTrenchCoatToShoppingBasket(const TrenchCoat& trench_coat)
{
	auto iterator = std::find(userShoppingBasket.begin(), userShoppingBasket.end(), trench_coat);
	int quantity = trench_coat.getQuantity();
	if (quantity == 1)
		deleteTrenchCoat(trench_coat);
	else {
		quantity--;
		TrenchCoat trench_coat2 = trench_coat;
		trench_coat2.setQuantity(quantity);
		updateTrenchCoat(trench_coat, trench_coat2);
	}
	userShoppingBasket.push_back(trench_coat);
	return 1;
}
*/