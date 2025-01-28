#ifndef MOCK_DATA_H
#define MOCK_DATA_H

#include <map>
#include "House.h"
#include "Positions.h"
#include "ResponseWrapper.h"
#include "Status.h"


namespace com::cjmobileapps::quidditchplayers::data {
    class MockData {
    public:
        static const model::Status &getMockStatus();

        static std::string getStatus(const std::string &name);

        static const model::ResponseWrapper<std::vector<model::House> > &getMockHousesResponseWrapper();

        static const std::vector<model::House> &getMockHouses();

        static const std::map<int, model::Position> &getMockPositions();

        static const model::ResponseWrapper<std::map<int, model::Position> > &getMockPositionsResponseWrapper();
    };
}

#endif // MOCK_DATA_H
