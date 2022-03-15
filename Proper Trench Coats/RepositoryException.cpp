#include "RepositoryException.h"

RepositoryException::RepositoryException(std::string exceptionMessage) : exceptionMessage{ exceptionMessage } {
    generatedError = "Repository error: " + exceptionMessage;
}

const char* RepositoryException::what() const noexcept {
    return generatedError.c_str();
}