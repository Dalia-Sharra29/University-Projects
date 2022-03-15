#include "CSVShoppingBasket.h"
#include <fstream>
#include <Windows.h>
#include "RepositoryException.h"

void CSVShoppingBasket::writeToFile()
{
    if (this->fileName == "")
        return;
    std::ofstream fout(this->fileName);
    if (!fout.is_open())
        return;
    if (this->coats.empty())
        return;
    for (const TrenchCoat& trench_coat : this->coats)
    {
        fout << trench_coat;
    }
    fout.close();
}

void CSVShoppingBasket::displayShoppingBasket() const
{
    std::string aux = "\"" + this->fileName + "\""; 
    ShellExecuteA(NULL, NULL, "D:\\Facultate\\ASC\\ASM_tools\\asm_tools\\npp\\notepad++.exe", aux.c_str(), NULL, SW_SHOWMAXIMIZED);
}