#pragma once
#include "Repository.h"
#include "FileShoppingBasket.h"
#include "TrenchCoatValidator.h"
#include <memory>
#include "Action.h"

class Service
{
private:
	Repository& repository;
	FileShoppingBasket* shoppingBasket;
	TrenchCoatValidator validator;
	std::vector<Action*> undoStack;
	std::vector<Action*> redoStack;


public:
	Service(Repository& repo, FileShoppingBasket* s, TrenchCoatValidator v) : repository{ repo }, shoppingBasket{ s }, validator{ v } {}

	Repository getRepo() const { return repository; }
	ShoppingBasket* getShoppingBasket() const { return shoppingBasket; }

	/*
	* The function adds a new trench coat in the list
	* Input: "size" - a positive integer, representig the size of the trench coat to add
	*		 "colour" - a string, representig the colour of the trench coat to add
	*		 "price" - a positive integer, representig the price of the trench coat to add
	*		 "quantity" - a positive integer, representig the quantity of the trench coat to add
	*		 "photograph" - a link, representig the link to the trench coat to add
	*/
	void addTrenchCoatToRepository(int size, const std::string& colour, double price, int quantity, const std::string& photograph);

	/*
	* The function deletes a trench coat from the list
	* Input: "size" - a positive integer, representig the size of the trench coat to delete
	*		 "colour" - a string, representig the colour of the trench coat to delete
	*/
	void deleteTrenchCoatFromRepository(int size, const std::string& colour);

	/*
	* The function updates a trench coat from the list
	* Input: "size" - a positive integer, representig the size of the trench coat to update
	*		 "colour" - a string, representig the colour of the trench coat to update
	*		 "size" - a positive integer, representig the new size to update with
	*		 "colour" - a string, representig the new colour to update with
	*		 "price" - a positive integer, representig the new price to update with
	*		 "quantity" - a positive integer, representig the new quantity to update with
	*		 "photograph" - a link, representig the new link to to update with
	*/
	void updateTrenchCoat(int size, const std::string& colour, int new_size, const std::string& new_colour, double price, int quantity, const std::string& photograph);

	void addTrenchCoatToShoppingBasket(const TrenchCoat& trench_coat);

	void firstCoat(int size);

	void nextTrenchCoatShoppingBasket(int size);

	void saveShoppingBasket(const std::string& fileName);

	void openShoppingBasket() const;

	double getCurrentSum();

	std::vector<TrenchCoat> getAllCoats();

	void undo();

	void redo();
};