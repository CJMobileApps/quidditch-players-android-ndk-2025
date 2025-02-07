#include "Player.h"
#include <string>
#include <vector>
#include <House.h>

namespace com::cjmobileapps::quidditchplayers::model {

    Player::Player(std::string id, std::string firstName, std::string lastName, std::vector<int> yearsPlayed,
                   std::string favoriteSubject, const int position, std::string imageUrl, const HouseName house)
        : id(std::move(id)), firstName(std::move(firstName)), lastName(std::move(lastName)),
          yearsPlayed(std::move(yearsPlayed)), favoriteSubject(std::move(favoriteSubject)),
          position(position), imageUrl(std::move(imageUrl)), house(house) {}

    std::string Player::getId() const {
        return id;
    }

    std::string Player::getFirstName() const {
        return firstName;
    }

    std::string Player::getLastName() const {
        return lastName;
    }

    std::vector<int> Player::getYearsPlayed() const {
        return yearsPlayed;
    }

    std::string Player::getFavoriteSubject() const {
        return favoriteSubject;
    }

    int Player::getPosition() const {
        return position;
    }

    std::string Player::getImageUrl() const {
        return imageUrl;
    }

    HouseName Player::getHouse() const {
        return house;
    }
}
