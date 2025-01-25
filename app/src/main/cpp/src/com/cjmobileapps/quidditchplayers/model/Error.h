#ifndef ERROR_H
#define ERROR_H

#include <string> // For std::string

namespace com::cjmobileapps::quidditchplayers::model {
    class Error {
    public:
        // Constructors
        Error(bool isError = false, std::string message = "");

        // Destructor
        ~Error() = default;

        // Getters
        bool getIsError() const;

        std::string getMessage() const;

        // Setters
        void setIsError(bool isError);

        void setMessage(const std::string &message);

        //todo undo
    private:
        bool isError; // Indicates if it is an error
        std::string message; // Message associated with the error
    };
}

#endif // ERROR_H
