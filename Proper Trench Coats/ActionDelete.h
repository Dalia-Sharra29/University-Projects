#pragma once
#include "Action.h"
#include "Repository.h"
class ActionDelete : public Action
{
private:
	Repository& repository;
	TrenchCoat deletedCoat;

public:
	ActionDelete(Repository& repo, TrenchCoat tc) : repository{ repo }, deletedCoat{ tc }, Action() {}
	void executeUndo() override;
	void executeRedo() override;
	virtual ~ActionDelete();
};

