#ifndef MOCK_DATA_H
#define MOCK_DATA_H

#include "House.h"
#include "ResponseWrapper.h"
#include "Status.h"

namespace com::cjmobileapps::quidditchplayers::data {
    class MockData {
    public:
        static const model::Status &getMockStatus();

        static std::string getStatus(const std::string &name);

        static const model::ResponseWrapper<std::vector<model::House>> &getMockHousesResponseWrapper();

        static const std::vector<model::House> &getMockHouses();
    };
}

#endif // MOCK_DATA_H
