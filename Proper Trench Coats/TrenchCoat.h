#pragma once
#include <string>

class TrenchCoat
{
private:
	int size;
	std::string colour;
	double price;
	int quantity;
	std::string photograph;
public:
	//Trench Coat constructor
	TrenchCoat(int size = 0, const std::string& colour = "", double price = 0, int quantity = 0, const std::string& photograph = "");

	//Trench Coat copy constructor
	TrenchCoat(const TrenchCoat& trenchcoat);

	/*
	* Size setter
	* Input: "size" - the value to set with
	* Output: -
	*/
	void setSize(int size);

	/*
	* Colour setter
	* Input: "colour" - the string to set with
	* Output: -
	*/
	void setColour(const std::string& colour);

	/*
	* Price setter
	* Input: "price" - the value to set with
	* Output: -
	*/
	void setPrice(double price);

	/*
	* Quantity setter
	* Input: "quantity" - the value to set with
	* Output: -
	*/
	void setQuantity(int quantity);

	/*
	* Photograph setter
	* Input: "photograph" - the string to set with
	* Output: -
	*/
	void setPhotograph(const std::string& photograph);

	/*
	* Size getter
	* Input: -
	* Output: the size of the trench coat
	*/
	int getSize() const;

	/*
	* Colour getter
	* Input: -
	* Output: the colour of the trench coat
	*/
	const std::string& getColour() const;

	/*
	* Price getter
	* Input: -
	* Output: the price of the trench coat
	*/
	double getPrice() const;

	/*
	* Quantity getter
	* Input: -
	* Output: the quantity of the trench coat
	*/
	int getQuantity() const;

	/*
	* Photograph getter
	* Input: -
	* Output: the photograph of the trench coat
	*/
	const std::string& getPhotograph() const;

	/*
	* Overwrites the assignment (=) operator
	*/
	void operator=(const TrenchCoat& trench_coat);

	/*
	* Overwrites the equality (==) operator
	*/
	bool operator==(const TrenchCoat& trench_coat) const;

	/*
	* Nice way to print the trench coat
	*/
	std::string toString() const;

	// Plays the current song: the page corresponding to the source link is opened in a browser.
	void play();

	friend std::ostream& operator<< (std::ostream& outStream, const TrenchCoat& trench_coat);
	friend std::istream& operator>> (std::istream& inStream, TrenchCoat& trench_coat);
};