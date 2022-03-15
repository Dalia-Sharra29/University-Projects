#include "HTMLShoppingBasket.h"
#include <fstream>
#include <Windows.h>
#include "RepositoryException.h"

void HTMLShoppingBasket::writeToFile()
{
    if (this->fileName == "")
        return;
    std::ofstream fout(this->fileName);
    if (!fout.is_open())
        return;
    fout << "<!DOCTYPE html>\n<html><head><title>ShoppingBasket</title></head><body>\n";
    fout << "<table border=\"1\">\n";
    fout
        << "<tr><td>Size</td><td>Colour</td><td>Price</td><td>Quantity</td><td>Photograph</td></tr>\n";
    for (const TrenchCoat& trench_coat : this->coats) {
        fout << "<tr><td>" << trench_coat.getSize() << "</td>"
            << "<td>" << trench_coat.getColour() << "</td>"
            << "<td>" << trench_coat.getPrice() << "</td>"
            << "<td>" << trench_coat.getQuantity() << "</td>"
            << "<td><a href=\"" << trench_coat.getPhotograph() << "\">" << trench_coat.getPhotograph() << "</a></td></tr>" << '\n';
    }
    fout << "</table></body></html>";
    fout.close();
}

void HTMLShoppingBasket::displayShoppingBasket() const
{
    std::string aux = "\"" + this->fileName + "\"";
    ShellExecuteA(NULL, "open", aux.c_str(), NULL, NULL, SW_SHOWNORMAL);
}