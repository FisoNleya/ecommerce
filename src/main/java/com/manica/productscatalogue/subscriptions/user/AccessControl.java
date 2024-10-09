package com.manica.productscatalogue.subscriptions.user;


import com.manica.productscatalogue.auth.dtos.AuthenticationUser;
import com.manica.productscatalogue.auth.dtos.Permission;
import com.manica.productscatalogue.auth.dtos.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccessControl {


    public static boolean hasAdminUpdate(AuthenticationUser user) {
        if (user.getRole().getPermissions().parallelStream()
                .unordered()
                .anyMatch(perm -> perm.equals(Permission.ADMIN_UPDATE)))
            return true;
        else
            return false;
    }

    public static boolean hasVendorUpdate(AuthenticationUser user) {
        if (user.getRole().equals(Role.ADMIN))
            return true;

        else if (user.getRole().getPermissions().parallelStream()
                .anyMatch(perm -> perm.equals(Permission.VENDOR_UPDATE)))
            return true;

        else
            return false;
    }


    public static boolean hasRightsInVendor(long vendorId, AuthenticationUser user, User savedUser) {

        if (user.getRole().getPermissions().parallelStream()
                .unordered()
                .anyMatch(perm -> perm.equals(Permission.ADMIN_READ)))
            return true;
        else if (user.getRole().getPermissions().parallelStream()
                .anyMatch(perm -> perm.equals(Permission.VENDOR_READ))&&
                savedUser.getUserRights().stream().anyMatch(ur-> ur.getVendor().getVendorId() == vendorId)
        )
            return true;

        else
            return false;
    }


}
