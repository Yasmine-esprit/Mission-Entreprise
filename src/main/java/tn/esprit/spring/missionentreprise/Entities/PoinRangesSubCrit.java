package tn.esprit.spring.missionentreprise.Entities;

import lombok.Getter;


@Getter

public enum PoinRangesSubCrit {
        FIVE_TO_FOUR("5–4 points"),
        THREE_TO_TWO("3–2 points"),
        ONE_TO_ZERO("1–0 points");

        private final String label;

    PoinRangesSubCrit(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

