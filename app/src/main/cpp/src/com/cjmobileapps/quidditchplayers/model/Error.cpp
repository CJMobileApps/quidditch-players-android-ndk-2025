#include "Error.h"

namespace com::cjmobileapps::quidditchplayers::model {
    // Constructor
    Error::Error(bool isError, std::string message)
        : isError(isError), message(std::move(message)) {
    }

    // Getters
    bool Error::getIsError() const {
        return isError;
    }

    std::string Error::getMessage() const {
        return message;
    }

    // Setters
    void Error::setIsError(bool isError) {
        this->isError = isError;
    }

    void Error::setMessage(const std::string &message) {
        this->message = message;
    }
}
