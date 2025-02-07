#include "Status.h"

namespace com::cjmobileapps::quidditchplayers::model {
    Status::Status(std::string playerId, std::string status)
        : playerId(std::move(playerId)), status(std::move(status)) {
    }
}
