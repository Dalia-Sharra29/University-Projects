#pragma once
#include "TrenchCoat.h"
#include <vector>
#include <algorithm>
#include "RepositoryException.h"

class Repository
{
private:
	std::vector<TrenchCoat> coats_list;
	int current;
	//std::vector<TrenchCoat> userShoppingBasket;
	std::string coatsFileName;

public:

	// Reads from the file
	void loadCoatsFromFile();

	// Writes to the file
	void writeCoatsToFile();

	// Repository constructor
	Repository(const std::string& fileName = "");

	// Repository destructor
	~Repository();

	/*
	* The function adds a new trench coat in the list
	* Input: the trench coat to add
	* Output: -1 - if the given trench coat already exists
	*		   1 - otherwise
	*/
	void addTrenchCoat(const TrenchCoat& trench_coat);

	/*
	* The function deletes a trench coat from the list
	* Input: the trench coat to delete
	* Output: -1 - if the given trench coat does not exist
	*		   1 - otherwise
	*/
	void deleteTrenchCoat(const TrenchCoat& trench_coat);

	/*
	* The function updates a trench coat from the list
	* Input: the trench coat to update
	*		 the new trench coat to update with
	* Output: -1 - if the given trench coat does not exist
	*		   1 - otherwise
	*/
	void updateTrenchCoat(const TrenchCoat& trench_coat, const TrenchCoat& new_trench_coat);

	/*
	* The function search for a trench coat in the list
	* Input: the trench coat to search for
	* Output: -1 - if the given trench coat does not exist
	*		   the trench coat - an integer, representing the position where the trench coat exist in the list
	*/
	TrenchCoat findTrenchCoat(const TrenchCoat& trench_coat);

	/*
	* The function returns the list of trench coats
	*/
	std::vector<TrenchCoat> getAllCoats();

	/*
	* The function returns the number of existing trench coats in the list
	*/
	int getNumberOfCoats();

	/*
	* The function returns the list of trench coats with a given size
	*/
	std::vector<TrenchCoat> getTrenchCoatsBySize(int size);

	/*
	* The function returns the list of trench coats from the shopping basket
	*/
	//std::vector<TrenchCoat> getShoppingBasket();

	/*
	* The function adds the given trench coat in the shopping basket list
	* Input: the trench coat to add
	* Output: -1 - if the given trench coat already exists
	*		   1 - otherwise
	*/
	//int addTrenchCoatToShoppingBasket(const TrenchCoat& trench_coat);

	TrenchCoat getCurrentCoat();

	void setCurrent(int &position);

	void play();

	void next();

	void first();

};