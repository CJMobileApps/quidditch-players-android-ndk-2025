#ifndef PLAYER_H
#define PLAYER_H

#include <string>
#include <vector>
#include <House.h>


namespace com::cjmobileapps::quidditchplayers::model {
    class Player {
    public:
        Player(std::string id, std::string firstName, std::string lastName, std::vector<int> yearsPlayed,
               std::string favoriteSubject, int position, std::string imageUrl, HouseName house);

        // Getters
        std::string getId() const;

        std::string getFirstName() const;

        std::string getLastName() const;

        std::vector<int> getYearsPlayed() const;

        std::string getFavoriteSubject() const;

        int getPosition() const;

        std::string getImageUrl() const;

        HouseName getHouse() const;

    private:
        std::string id;
        std::string firstName;
        std::string lastName;
        std::vector<int> yearsPlayed;
        std::string favoriteSubject;
        int position;
        std::string imageUrl;
        HouseName house;
    };
}

#endif // PLAYER_H
