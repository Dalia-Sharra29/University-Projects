#pragma once
#include "Action.h"
#include "Repository.h"

class ActionUpdate : public Action
{
private:
	Repository& repository;
	TrenchCoat oldCoat;
	TrenchCoat newCoat;

public:
	ActionUpdate(Repository& repo, TrenchCoat newTrench, TrenchCoat oldTrench) : repository{ repo }, newCoat{ newTrench }, oldCoat{ oldTrench }, Action() {}
	void executeUndo() override;
	void executeRedo() override;
	virtual ~ActionUpdate();

};

