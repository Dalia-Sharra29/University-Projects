#include "TrenchCoat.h"
#include <vector>
#include <sstream>
#include <windows.h>
#include <shellapi.h>

TrenchCoat::TrenchCoat(int size, const std::string& colour, double price, int quantity, const std::string& photograph) : size(size),
colour(colour), price(price), quantity(quantity), photograph(photograph)
{

}

TrenchCoat::TrenchCoat(const TrenchCoat& trench_coat)
{
	this->size = trench_coat.size;
	this->colour = trench_coat.colour;
	this->price = trench_coat.price;
	this->quantity = trench_coat.quantity;
	this->photograph = trench_coat.photograph;
}

void TrenchCoat::setSize(int size)
{
	this->size = size;
}

void TrenchCoat::setColour(const std::string& colour)
{
	this->colour = colour;
}

void TrenchCoat::setPrice(double price)
{
	this->price = price;
}

void TrenchCoat::setQuantity(int quantity)
{
	this->quantity = quantity;
}

void TrenchCoat::setPhotograph(const std::string& photograph)
{
	this->photograph = photograph;
}

int TrenchCoat::getSize() const
{
	return this->size;
}

const std::string& TrenchCoat::getColour() const
{
	return this->colour;
}

double TrenchCoat::getPrice() const
{
	return this->price;
}

int TrenchCoat::getQuantity() const
{
	return this->quantity;
}

const std::string& TrenchCoat::getPhotograph() const
{
	return this->photograph;
}

void TrenchCoat::operator=(const TrenchCoat& trench_coat)
{
	size = trench_coat.getSize();
	colour = trench_coat.getColour();
	price = trench_coat.getPrice();
	quantity = trench_coat.getQuantity();
	photograph = trench_coat.getPhotograph();
}

bool TrenchCoat::operator==(const TrenchCoat& trench_coat) const
{
	if (size == trench_coat.size && colour == trench_coat.colour)
	{
		return true;
	}
	return false;
}

std::string TrenchCoat::toString() const
{
	std::string output = "SIZE: " + std::to_string(size) + " | COLOUR: " + colour + " | PRICE: " + std::to_string(price) + " | QUANTITY: " + std::to_string(quantity) + " | PHOTOGRAPH: " + photograph;
	return output;
}

std::vector<std::string> tokenize(std::string str, char delimiter)
{
	std::vector <std::string> result;
	std::stringstream ss(str);
	std::string token;
	while (std::getline(ss, token, delimiter))
		result.push_back(token);

	return result;
}

void TrenchCoat::play()
{
	std::string url = this->photograph;
	ShellExecuteA(NULL, NULL, url.c_str(), "", NULL, SW_SHOWMAXIMIZED);
}

std::ostream& operator<<(std::ostream& outStream, const TrenchCoat& trench_coat)
{
	outStream << std::to_string(trench_coat.getSize()) << "," << trench_coat.getColour() << "," << std::to_string(trench_coat.getPrice()) << "," << std::to_string(trench_coat.getQuantity()) << "," << trench_coat.getPhotograph() << "\n";
	return outStream;
}

std::istream& operator>>(std::istream& inStream, TrenchCoat& trench_coat)
{
	std::string line;
	std::getline(inStream, line);
	std::vector <std::string> tokens = tokenize(line, ',');
	if (tokens.size() != 5)
		return inStream;
	trench_coat.size = stoi(tokens[0]);
	trench_coat.colour = tokens[1];
	trench_coat.price = stoi(tokens[2]);
	trench_coat.quantity = stoi(tokens[3]);
	trench_coat.photograph = tokens[4];

	return inStream;
}