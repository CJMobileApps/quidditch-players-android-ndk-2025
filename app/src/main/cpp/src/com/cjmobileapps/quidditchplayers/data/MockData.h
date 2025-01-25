#ifndef MOCK_DATA_H
#define MOCK_DATA_H

#include "Status.h"

namespace com::cjmobileapps::quidditchplayers::data {
    class MockData {
    public:
        static const model::Status &getMockStatus();

        static std::string getStatus(const std::string &name);
    };
}

#endif // MOCK_DATA_H
