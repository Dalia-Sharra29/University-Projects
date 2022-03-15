#pragma once
class Action
{
public:
	Action();
	virtual void executeUndo() = 0;
	virtual void executeRedo() = 0;
	virtual ~Action();
};

