#include "Status.h"

#ifndef MOCK_DATA_H
#define MOCK_DATA_H

namespace com::cjmobileapps::quidditchplayers::data {
    class MockData {
    public:
        static const model::Status& getStatus();
    };
}

#endif // MOCK_DATA_H
