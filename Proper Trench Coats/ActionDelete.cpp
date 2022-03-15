#include "ActionDelete.h"

void ActionDelete::executeUndo()
{
	this->repository.addTrenchCoat(deletedCoat);
}

void ActionDelete::executeRedo()
{
	this->repository.deleteTrenchCoat(deletedCoat);
}

ActionDelete::~ActionDelete()
{
}
