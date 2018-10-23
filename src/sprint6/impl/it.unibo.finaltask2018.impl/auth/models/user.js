var bcrypt = require("bcrypt-nodejs");
var mongoose = require("mongoose");

/*
 * L'utente viene definito da un username unico,
 * da una password obbligatoria
 * e da un diplpayName e una bio opzionali
 */

var userSchema = mongoose.Schema({
  username: { type: String, required: true, unique: true },
  password: { type: String, required: true },
  displayName: String,
  bio: String
});

var SALT_FACTOR = 10;
var noop = function() {};

/*
 * La password non viene salvata in chiaro, ma ne
 * viene salvato un hash.
 */

userSchema.pre("save", function(done) {
  var user = this;

  if (!user.isModified("password")) {
    return done();
  }

  bcrypt.genSalt(SALT_FACTOR, function(err, salt) {
    if (err) { return done(err); }
    bcrypt.hash(user.password, salt, noop, function(err, hashedPassword) {
      if (err) { return done(err); }
      user.password = hashedPassword;
      done();
    });
  });
});

/*
 * Per la verifica della password non basta più una semplice
 * uguaglianza ma è necessario un metodo ad hoc
 */

userSchema.methods.checkPassword = function(guess, done) {
  bcrypt.compare(guess, this.password, function(err, isMatch) {
    done(err, isMatch);
  });
};

/*
 * Per la visualizzazione se è presente il displayName viene
 * utilizzato questo, altrimenti si usa semplicemente lo username
 */

userSchema.methods.name = function() {
  return this.displayName || this.username;
};

var User = mongoose.model("User", userSchema);

module.exports = User;
