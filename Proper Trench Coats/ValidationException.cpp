#include "ValidationException.h"

ValidationException::ValidationException(std::string exceptionMessage) :exceptionMessage{ exceptionMessage } {
	generatedError = "Validation error: " + exceptionMessage;
}

const char* ValidationException::what() const noexcept {
	return generatedError.c_str();
}