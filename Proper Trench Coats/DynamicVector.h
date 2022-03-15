#pragma once
#include "TrenchCoat.h"
#include <stdexcept>

typedef TrenchCoat TElem;

template <typename TElem>

class DynamicVector
{
private:
	TElem* elements;
	int length;
	int vector_capacity;

public:
	//Dynamic Vector constructor
	DynamicVector(int capacity = 10);

	//Dynamic Vector copy constructor
	DynamicVector(const DynamicVector& vector);

	//Dynamic Vector destructor
	~DynamicVector();

	/*
	* The function resize the dynamic vector
	*/
	void resize();

	/*
	* Search for an element in the dynamic vector
	* Input: an element
	* Output: the position where the element was found
	*	      -1 - if the element does not exist in the dynamic vector
	*/
	int search(const TElem& element);

	/*
	* Adds a new alement in the dynamic vector
	* Input: an element
	* Output: -
	*/
	void add(TElem element);

	/*
	* Removes an element from the dynamic vector
	* Input: an element
	* Output: -
	*/
	void remove(const TElem& element);

	/*
	* Updates an element from the dynamic vector
	* Input: the element to update
	*		 the element to update with
	* Output: -
	*/
	void update(const TElem& element, const TElem& new_element);

	/*
	* Overwrites the operator []
	* Input: a position of an element
	* Output: the element from that position in the dynamic vector
	*/
	TElem& operator[](int element_position);

	//Returns the size of the dynamic vector
	int size();

	//Returns the capacity of the dynamic vector
	int capacity();

	// Overwrites the operator =
	DynamicVector& operator=(const DynamicVector& vector);
};

template<typename TElem>
DynamicVector<TElem>::DynamicVector(int capacity)
{
	this->vector_capacity = capacity;
	this->length = 0;
	this->elements = new TElem[this->vector_capacity];
}

template<typename TElem>
DynamicVector<TElem>::DynamicVector(const DynamicVector& vector)
{
	this->vector_capacity = vector.vector_capacity;
	this->length = vector.length;
	this->elements = new TElem[vector.vector_capacity];
	for (int i = 0; i < this->length; i++)
	{
		this->elements[i] = vector.elements[i];
	}
}

template<typename TElem>
DynamicVector<TElem>::~DynamicVector()
{
	delete[] elements;
}

template<typename TElem>
inline void DynamicVector<TElem>::resize()
{
	TElem* newList = new TElem[vector_capacity * 2];
	for (int i = 0; i < length; i++)
	{
		newList[i] = elements[i];
	}
	vector_capacity *= 2;
	delete[] elements;
	elements = newList;
}

template<typename TElem>
int DynamicVector<TElem>::search(const TElem& element)
{
	for (int i = 0; i < length; i++)
	{
		if (elements[i] == element)
			return i;
	}
	return -1;

}

template<typename TElem>
void DynamicVector<TElem>::add(TElem element)
{
	if (length == vector_capacity)
		resize();
	elements[length] = element;
	length++;
}

template<typename TElem>
void DynamicVector<TElem>::remove(const TElem& element)
{
	int element_position = search(element);
	if (element_position != -1)
	{
		for (int i = element_position; i < length - 1; i++)
		{
			elements[i] = elements[i + 1];
		}
		length--;
	}
}

template<typename TElem>
void DynamicVector<TElem>::update(const TElem& element, const TElem& new_element)
{
	int element_position = search(element);
	if (element_position != -1)
	{
		elements[element_position] = new_element;
	}
}

template<typename TElem>
TElem& DynamicVector<TElem>::operator[](int element_position)
{
	if (element_position < 0 || element_position >= length)
		throw std::runtime_error("Invalid position!");
	return elements[element_position];
}

template<typename TElem>
int DynamicVector<TElem>::size()
{
	return length;
}

template<typename TElem>
int DynamicVector<TElem>::capacity()
{
	return vector_capacity;
}

template<typename TElem>
DynamicVector<TElem>& DynamicVector<TElem>::operator=(const DynamicVector& vector)
{
	if (this != &vector)
	{
		if (vector_capacity < vector.length)
		{
			delete[] elements;
			elements = new TElem[vector.vector_capacity];
		}
		length = vector.length;
		vector_capacity = vector.vector_capacity;
		for (int i = 0; i < length; i++)
		{
			elements[i] = vector.elements[i];
		}
	}
	return *this;
}