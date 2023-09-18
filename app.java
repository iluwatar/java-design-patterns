Potion createPotion(PotionType type) {
    var potion = potions.get(type);
    if (potion == null) {
      switch (type) {
        case HEALING -> {
          potion = new HealingPotion();
         
        }
        case HOLY_WATER -> {
          potion = new HolyWaterPotion();
         
        }
        case INVISIBILITY -> {
          potion = new InvisibilityPotion();
         
        }
        default -> {
        }
        potions.put(type, potion);
      }
    }
    return potion;
  }
