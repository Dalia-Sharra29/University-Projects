#include <string>

#ifndef EXCEPTION_H
#define EXCEPTION_H

class RepositoryException : public std::exception {
private:
    std::string exceptionMessage;
    std::string generatedError;

public:
    RepositoryException(std::string exceptionMessage = "");
    const char* what() const noexcept;
};
#endif