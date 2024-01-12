package com.example.utils;

import java.util.List;

import com.example.models.Organization;
import java.util.HashSet;
import java.util.Set;

public class PartyUtil {
    /**
     * Retrieves an array of unique party types from the database.
     *
     * @return an array of party type names
     */
    public static String[] getPartyTypes() {

        List<Organization> partyTypes = DBUtil.selectAllFromDB("organizations");

        Set<String> uniquePartyTypes = new HashSet<>();

        for (Organization partyType : partyTypes) {
            uniquePartyTypes.add(partyType.getOrgType());
        }

        String[] partyTypeNames = uniquePartyTypes.toArray(new String[0]);

        return partyTypeNames;
    }
}
