#include "TrenchCoatValidator.h"

void TrenchCoatValidator::validateTrenchCoat(const TrenchCoat& trench_coat)
{
	std::string errorsList = "";
	if (trench_coat.getSize() < 0)
		errorsList = "Invalid size ";
	if ((trench_coat.getColour()).size() == 0)
		errorsList += "Invalid colour ";
	if (trench_coat.getPrice() < 0.0)
		errorsList += "Invalid price ";
	if (trench_coat.getQuantity() <= 0)
		errorsList += "Invalid quantity ";
	if ((trench_coat.getPhotograph()).size() == 0)
		errorsList += "Invalid Photograph ";
	if (errorsList != "")
		throw ValidationException(errorsList);
}