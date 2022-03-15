#include "Service.h"
#include <algorithm>
#include "FileShoppingBasket.h"
#include "RepositoryException.h"
#include <iostream>
#include "ActionAdd.h"
#include "ActionDelete.h"
#include "ActionUpdate.h"
#include <exception>

void Service::addTrenchCoatToRepository(int size, const std::string& colour, double price, int quantity, const std::string& photograph)
{
	TrenchCoat trench_coat{ size, colour, price, quantity, photograph };
	this->validator.validateTrenchCoat(trench_coat);
	this->repository.addTrenchCoat(trench_coat);
	Action* action = new ActionAdd{ this->repository, trench_coat };
	this->redoStack.clear();
	this->undoStack.push_back(action);
}

void Service::deleteTrenchCoatFromRepository(int size, const std::string& colour)
{
	TrenchCoat trench_coat{ size, colour, 0, 1, "photograph" };
	TrenchCoat deletedCoat = this->repository.findTrenchCoat(trench_coat);
	this->validator.validateTrenchCoat(trench_coat);
	this->repository.deleteTrenchCoat(trench_coat);
	Action* action = new ActionDelete{ this->repository, deletedCoat };
	this->redoStack.clear();
	this->undoStack.push_back(action);
}

void Service::updateTrenchCoat(int size, const std::string& colour, int new_size, const std::string& new_colour, double price, int quantity, const std::string& photograph)
{
	TrenchCoat new_trench_coat{ new_size, new_colour, price, quantity, photograph };
	TrenchCoat trench_coat{ size, colour, 0, 1, "photograph" };
	TrenchCoat updatedCoat = this->repository.findTrenchCoat(trench_coat);
	this->validator.validateTrenchCoat(trench_coat);
	this->validator.validateTrenchCoat(new_trench_coat);
	this->repository.updateTrenchCoat(trench_coat, new_trench_coat);
	Action* action = new ActionUpdate{ this->repository, new_trench_coat, updatedCoat };
	this->redoStack.clear();
	this->undoStack.push_back(action);
}

void Service::addTrenchCoatToShoppingBasket(const TrenchCoat& trench_coat)
{
	if (this->shoppingBasket == nullptr)
		return;
	std::vector<TrenchCoat> coats = this->repository.getAllCoats();
	int quantity = trench_coat.getQuantity();
	if (quantity == 1)
	{
		deleteTrenchCoatFromRepository(trench_coat.getSize(), trench_coat.getColour());
	}
	else
	{
		quantity--;
		updateTrenchCoat(trench_coat.getSize(), trench_coat.getColour(), trench_coat.getSize(), trench_coat.getColour(), trench_coat.getPrice(), quantity, trench_coat.getPhotograph());
	}
	this->shoppingBasket->add(trench_coat);
}


void Service::firstCoat(int size)
{
	if (size == 0)
	{
		this->repository.first();
		return;
	}
	for (int i = 0; i < this->repository.getAllCoats().size(); i++)
		if (this->repository.getAllCoats()[i].getSize() == size)
			this->repository.setCurrent(i);
}

void Service::nextTrenchCoatShoppingBasket(int size)
{
	this->repository.next();
	if (size == 0)
	{
		return;
	}
	int cont = 0;
	while (this->repository.getCurrentCoat().getSize() != size)
	{
		this->repository.next();
		cont++;
		if (cont == this->repository.getCurrentCoat().getSize())
			break;
	}
}
 
void Service::saveShoppingBasket(const std::string& fileName)
{
	if (this->shoppingBasket == nullptr)
		return;
	this->shoppingBasket->setFileName(fileName);
	this->shoppingBasket->writeToFile();
}

void Service::openShoppingBasket() const
{
	if (this->shoppingBasket == nullptr)
		return;

	this->shoppingBasket->displayShoppingBasket();
}

std::vector<TrenchCoat> Service::getAllCoats()
{
	return this->repository.getAllCoats();
}

void Service::undo()
{
	if (this->undoStack.size() == 0)
	{
		throw std::exception("No more undos!");
	}
	Action* currentAction = this->undoStack[this->undoStack.size() - 1];
	currentAction->executeUndo();
	this->redoStack.push_back(currentAction);
	this->undoStack.pop_back();
}

void Service::redo()
{
	if (this->redoStack.size() == 0)
	{
		throw std::exception("No more redos!");
	}
	Action* currentAction = this->redoStack[this->redoStack.size() - 1];
	currentAction->executeRedo();
	this->undoStack.push_back(currentAction);
	this->redoStack.pop_back();
}

double Service::getCurrentSum()
{
	return this->shoppingBasket->getCurrentSum();
}