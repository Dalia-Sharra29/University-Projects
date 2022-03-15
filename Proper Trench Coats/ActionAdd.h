#pragma once
#include "Action.h"
#include "Repository.h"
class ActionAdd : public Action
{
private:
	Repository& repository;
	TrenchCoat addedCoat;

public:
	ActionAdd(Repository& repo, TrenchCoat tc) : repository{ repo }, addedCoat{ tc }, Action() {}
	void executeUndo() override;
	void executeRedo() override;
	virtual ~ActionAdd();
};


