#ifndef POSITIONS_H
#define POSITIONS_H

#include <string>
#include <map>

namespace com::cjmobileapps::quidditchplayers::model {
    class Position {
    public:
        std::string positionName;

        Position(const std::string &name) : positionName(name) {
        }
    };

    extern const int CHASER;
    extern const int BEATER;
    extern const int KEEPER;
    extern const int SEEKER;

    extern std::map<int, Position> mockPositions;
}

#endif // POSITIONS_H
