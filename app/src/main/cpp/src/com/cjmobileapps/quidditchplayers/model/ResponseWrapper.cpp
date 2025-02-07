#include "ResponseWrapper.h"
#include "Error.h"
#include "Status.h"
#include <map>
#include "House.h"
#include "Positions.h"

// Template member function implementations
namespace com::cjmobileapps::quidditchplayers::model {
    template<typename T>
    ResponseWrapper<T>::ResponseWrapper(T data, Error *error, int statusCode)
        : data(std::move(data)), error(error), statusCode(statusCode) {
    }

    template<typename T>
    ResponseWrapper<T>::~ResponseWrapper() {
        // if (error) {
        //     delete error;
        // }
    }

    template<typename T>
    ResponseWrapper<T>::ResponseWrapper(const ResponseWrapper &other)
        : data(other.data), statusCode(other.statusCode) {
        if (other.error) {
            error = new Error(*other.error);
        } else {
            error = nullptr;
        }
    }

    template<typename T>
    ResponseWrapper<T> &ResponseWrapper<T>::operator=(const ResponseWrapper &other) {
        if (this != &other) {
            data = other.data;
            statusCode = other.statusCode;
            if (error) {
                delete error;
            }
            error = other.error ? new Error(*other.error) : nullptr;
        }
        return *this;
    }
}


template class com::cjmobileapps::quidditchplayers::model::ResponseWrapper<int>;
template class com::cjmobileapps::quidditchplayers::model::ResponseWrapper<
    com::cjmobileapps::quidditchplayers::model::Status>;
template class com::cjmobileapps::quidditchplayers::model::ResponseWrapper<std::vector<
    com::cjmobileapps::quidditchplayers::model::House> >;
template class com::cjmobileapps::quidditchplayers::model::ResponseWrapper<
        std::map<int, com::cjmobileapps::quidditchplayers::model::Position>>;
