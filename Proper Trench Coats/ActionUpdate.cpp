#include "ActionUpdate.h"

void ActionUpdate::executeUndo()
{
	this->repository.updateTrenchCoat(newCoat, oldCoat);
}

void ActionUpdate::executeRedo()
{
	this->repository.updateTrenchCoat(oldCoat, newCoat);

}

ActionUpdate::~ActionUpdate()
{
}
