#include "ActionAdd.h"

void ActionAdd::executeUndo()
{
	this->repository.deleteTrenchCoat(addedCoat);
}

void ActionAdd::executeRedo()
{
	this->repository.addTrenchCoat(addedCoat);
}

ActionAdd::~ActionAdd()
{
}
