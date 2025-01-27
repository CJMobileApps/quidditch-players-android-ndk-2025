#include "House.h"

namespace com::cjmobileapps::quidditchplayers::model {
    // Constructor
    House::House(int houseId, HouseName name, const std::string& imageUrl, const std::string& emoji)
        : houseId(houseId), name(name), imageUrl(imageUrl), emoji(emoji) {}

    // Getters
    int House::getHouseId() const {
        return houseId;
    }

    HouseName House::getName() const {
        return name;
    }

    std::string House::getImageUrl() const {
        return imageUrl;
    }

    std::string House::getEmoji() const {
        return emoji;
    }

    // Setters
    void House::setHouseId(int houseId) {
        this->houseId = houseId;
    }

    void House::setName(HouseName name) {
        this->name = name;
    }

    void House::setImageUrl(const std::string& imageUrl) {
        this->imageUrl = imageUrl;
    }

    void House::setEmoji(const std::string& emoji) {
        this->emoji = emoji;
    }
}
