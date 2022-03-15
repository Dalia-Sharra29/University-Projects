#pragma once
#include <string>

class ValidationException : public std::exception {
private:
	std::string exceptionMessage;
	std::string generatedError;
public:
	ValidationException(std::string exceptionMessage = "");
	const char* what() const noexcept;
};