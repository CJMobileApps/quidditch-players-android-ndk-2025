#ifndef RESPONSE_WRAPPER_H
#define RESPONSE_WRAPPER_H

#include "Error.h"

namespace com::cjmobileapps::quidditchplayers::model {

    // Forward declaration of Error class
    class Error;

    template <typename T>
    class ResponseWrapper {
    public:
        // Public members
        T data;           // The main data of the wrapper
        Error* error;     // Pointer to Error (nullable equivalent)
        int statusCode;   // HTTP-like status code

        // Constructor
        ResponseWrapper(T data = T(), Error* error = nullptr, int statusCode = 999);

        // Destructor
        ~ResponseWrapper();

        // Copy constructor
        ResponseWrapper(const ResponseWrapper& other);

        // Assignment operator
        ResponseWrapper& operator=(const ResponseWrapper& other);
    };

} // namespace com::cjmobileapps::quidditchplayers::model

#endif // RESPONSE_WRAPPER_H
