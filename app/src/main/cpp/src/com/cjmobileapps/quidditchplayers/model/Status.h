#ifndef STATUS_H
#define STATUS_H

#include <string>

namespace com::cjmobileapps::quidditchplayers::model {
    class Status {
    public:
        std::string playerId;
        std::string status;

        Status(const std::string &playerId, const std::string &status);
    };
}

#endif // STATUS_H
