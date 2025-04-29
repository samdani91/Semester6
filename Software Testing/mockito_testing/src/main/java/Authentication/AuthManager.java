package Authentication;

public class AuthManager {
    private UserRepo userRepo = new UserRepo();
    private Hash hash = new Hash();

    public AuthManager(UserRepo userRepo, Hash hash) {
        this.userRepo = userRepo;
        this.hash = hash;
    }


    public boolean login(String email, String password) {
        User user;
        try{
            user = userRepo.findByEmail(email);
        } catch (Exception e) {
            throw e;
        }

        return hash.verify(password, user.getHashedPassword());

    }
}
