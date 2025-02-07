#ifndef STATUS_H
#define STATUS_H

#include <string>

namespace com::cjmobileapps::quidditchplayers::model {
    class Status {
    public:
        std::string playerId;
        std::string status;

        Status(std::string playerId, std::string status);
    };
}

#endif // STATUS_H
