#include "Status.h"

namespace com::cjmobileapps::quidditchplayers::model {
    Status::Status(const std::string &playerId, const std::string &status)
        : playerId(playerId), status(status) {
    }
}
