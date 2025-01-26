#ifndef HOUSE_H
#define HOUSE_H

#include <string>
#include <vector>

namespace com::cjmobileapps::quidditchplayers::model {
    // Enum for HouseName
    enum class HouseName {
        GRYFFINDOR,
        SLYTHERIN,
        RAVENCLAW,
        HUFFLEPUFF
    };

    // Class for House
    class House {
    public:
        // Constructor
        House(int houseId = 0, HouseName name = HouseName::GRYFFINDOR,
              const std::string& imageUrl = "", const std::string& emoji = "");

        // Getters
        int getHouseId() const;
        HouseName getName() const;
        std::string getImageUrl() const;
        std::string getEmoji() const;

        // Setters
        void setHouseId(int houseId);
        void setName(HouseName name);
        void setImageUrl(const std::string& imageUrl);
        void setEmoji(const std::string& emoji);

    private:
        int houseId;
        HouseName name;
        std::string imageUrl;
        std::string emoji;
    };
}

#endif // HOUSE_H
